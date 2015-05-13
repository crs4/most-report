# -*- coding: utf-8 -*-

#
# Project MOST - Moving Outcomes to Standard Telemedicine Practice
# http://most.crs4.it/
#
# Copyright 2014, CRS4 srl. (http://www.crs4.it/)
# Dual licensed under the MIT or GPL Version 2 licenses.
# See license-GPLv2.txt or license-MIT.txt

from django.forms import ModelForm
from django.core.exceptions import ValidationError
from django.utils.translation import ugettext_lazy as _
from most.web.report.models import ExamNode, NodeReport, NodeValue


class NodeValueForm(ModelForm):
    class Meta:
        model = NodeValue


class NodeReportForm(ModelForm):
    class Meta:
        model = NodeReport


class ExamNodeForm(ModelForm):
    class Meta:
        model = ExamNode
        exclude = [
            'clinician',
            'creation_datetime',
            'modification_datetime',
            'deletion_datetime',
        ]
