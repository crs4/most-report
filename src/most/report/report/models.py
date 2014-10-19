# -*- coding: utf-8 -*-

#
# Project MOST - Moving Outcomes to Standard Telemedicine Practice
# http://most.crs4.it/
#
# Copyright 2014, CRS4 srl. (http://www.crs4.it/)
# Dual licensed under the MIT or GPL Version 2 licenses.
# See license-GPLv2.txt or license-MIT.txt

from django.db import models
from django.core.exceptions import ValidationError
from django.utils.translation import ugettext_lazy as _
from most.web.users.models import ClinicianUser
from most.web.exams.models import Section


class ExamNode(models.Model):
    """Define the structure of an exam, by his component node definition"""
    # exam section, only for root node
    section = models.ForeignKey(Section, verbose_name=_("Section"), related_name="section_node", db_column="section_node", blank=True, null=True)
    # node name
    name = models.CharField(_('Exam Node Name'), max_length=25)
    # true if the node is the starting point of a branch
    root_node = models.BooleanField(_("Root node"), default=False)
    # true if the node if the default choice on the traversing path
    default_node = models.BooleanField(_("Default value"), default=False)
    # order number between sibling
    order_number = models.IntegerField(_("Order number"), null=True, blank=True, default=None)
    # children nodes of current node
    children = models.ManyToManyField("self", verbose_name=_("Children"), related_name="father_node", symmetrical=False, db_column="child_node")
    # saved path on the journey to the current node
    cached_path = models.CharField(_("Cached Path"), max_length=500, null=True, blank=True)
    # true if the path exclude to walk through sibling nodes
    unique_node = models.BooleanField(_("Unique"), default=False)

    def __unicode__(self):
        return self.name

    def is_leaf(self):
        return False if self.children.all() else True

    def get_parent_node(self):
        try:
            parent_node = ExamNode.objects.get(children__pk=self.pk)
        except:
            parent_node = None
        return parent_node

    def get_root_node(self):
        if self.root_node:
            return self
        parent_node = self.get_parent_node()
        while not parent_node.root_node:
            parent_node = parent_node.get_parent_node()
        return parent_node

    def get_path(self):
        if self.root_node:
            return "/%s/%s" % (self.section.name, self.pk)
        subpath = "/%s" % self.pk
        father = self.get_parent_node()
        while not father.root_node:
            subpath = "/%s" % father.pk + subpath
            father = father.get_parent_node()
        else: #aggiungo in testa il nome dell'esame in minuscolo perche' sulla root dell'albero e' lower
            subpath = "/%s/%s%s" % (father.section.name.lower(), father.pk, subpath)
        return subpath

    def get_sibling(self):
        try:
            parent_node = self.get_parent_node()
            sibling_list = parent_node.children.all().exclude(pk=self.pk)
        except:
            sibling_list = None
        return sibling_list


class NodeReport(models.Model):
    exam_node = models.ForeignKey("ExamNode", verbose_name=_("Exam Node"), related_name="node_report", db_column="node_description", blank=True, null=True)
    separator = models.CharField(_("Separator"), max_length=5, null=True, blank=True)
    ignore_label = models.BooleanField(_("Ignore label"), default=False)

    def __unicode__(self):
        return self.exam_node


class NodeValue(models.Model):
    VALUE_TYPE_CHOICES = (
        ('N', 'Number'),
        ('T', 'Text'),
        ('U', 'Undefined')
    )

    exam_report = models.ForeignKey("NodeReport", null=True, blank=True, verbose_name=_("Node Report"), related_name="node_value", db_column="node_value")
    value = models.CharField(_("Value"), max_length=500, null=True, blank=True)
    unit_measure = models.CharField(_("Unit of measure"), max_length=50, null=True, blank=True)
    value_type = models.CharField(_("Value type"), choices=VALUE_TYPE_CHOICES, max_length=1, default='U')

    def __unicode__(self):
        value_string = u'[%s] %s' % (self.value_type, self.exam_report)
        if self.value:
            value_string = u'%s = %s' % (value_string, self.value)
            if self.unit_measure:
                value_string = u'%s = %s' % (value_string, self.unit_measure)
        return value_string