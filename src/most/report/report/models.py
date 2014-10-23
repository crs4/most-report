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

    def to_dictionary(self, exclude_children=False):
        exam_node_dictionary = {
            u'id': u'%s' % self.id,
            u'section': u'%s' % self.section.to_dictionary(),
            u'name': u'%s' % self.name,
            u'root_node': u'%s' % self.root_node,
            u'default_node': u'%s' % self.default_node,
            u'order_number': u'%s' % self.order_number if self.order_number else None,
            u'cached_path': u'%s' % self.cached_path if self.cached_path else None,
            u'unique_node': u'%s' % self.unique_node
        }
        #children
        if not exclude_children:
            child_list = []
            for child in exclude_children:
                child_list.append(child.to_dictionary(exclude_children=True))
        return exam_node_dictionary


class NodeReport(models.Model):
    VALUE_TYPE_CHOICES = (
        ('N', 'Number'),
        ('T', 'Text'),
        ('U', 'Undefined')
    )

    exam_node = models.ForeignKey("ExamNode", verbose_name=_("Exam Node"), related_name="node_report", db_column="node_description", blank=True, null=True)
    separator = models.CharField(_("Separator"), max_length=5, null=True, blank=True)
    ignore_label = models.BooleanField(_("Ignore label"), default=False)
    unit_measure = models.CharField(_("Unit of measure"), max_length=50, null=True, blank=True)
    value_type = models.CharField(_("Value type"), choices=VALUE_TYPE_CHOICES, max_length=1, default='U')

    def __unicode__(self):
        return self.exam_node

    def to_dictionary(self):
        return {
            u'id': u'%s' % self.id,
            u'exam_node': u'%s' % self.exam_node.to_dictionary(exclude_children=True),
            u'separator': u'%s' % self.separator if self.separator else None,
            u'ignore_label': u'%s' % self.ignore_label,
            u'unit_measure': u'%s' % self.unit_measure if self.unit_measure else None,
            u'value_type': {
                u'key': u'%s' % self.value_type,
                u'value': u'%s' % self.get_value_type_display(),
            },
        }


class NodeValue(models.Model):
    clinician = models.ForeignKey(ClinicianUser, verbose_name='Clinician')
    creation_datetime = models.DateTimeField(_('Creation DateTime'), auto_now_add=True)
    modification_datetime = models.DateTimeField(_('Modification DateTime'), auto_now=True)
    deletion_datetime = models.DateTimeField(_('Deletion Date Time'), null=True, blank=True)
    node_report = models.ForeignKey("NodeReport", null=True, blank=True, verbose_name=_("Node Report"), related_name="node_value", db_column="node_value")
    value = models.CharField(_("Value"), max_length=500, null=True, blank=True)

    def __unicode__(self):
        value_string = u'[%s] %s' % (self.node_report.value_type, self.node_report)
        if self.value:
            value_string = u'%s = %s' % (value_string, self.value)
            if self.node_report.unit_measure:
                value_string = u'%s = %s' % (value_string, self.node_report.unit_measure)
        return value_string

    def to_dictionary(self):
        return {
            u'id': u'%s' % self.id,
            u'value': u'%s' % self.value if self.value else None,
            u'clinician': u'%s' % self.clinician.to_dictionary(),
            u'node_report': u'%s' % self.node_report.to_dictionary(),
        }