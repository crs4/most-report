# -*- coding: utf-8 -*-

#
# Project MOST - Moving Outcomes to Standard Telemedicine Practice
# http://most.crs4.it/
#
# Copyright 2014, CRS4 srl. (http://www.crs4.it/)
# Dual licensed under the MIT or GPL Version 2 licenses.
# See license-GPLv2.txt or license-MIT.txt

from django.conf.urls import patterns, include, url
from most.report.report.views import exam_node, node_report, node_value #, demo
from django.contrib import admin
admin.autodiscover()


urlpatterns = patterns('',
    # Examples:
    # url(r'^demo/', demo.examples),
    url(r'^admin/', include(admin.site.urls)),
)

# ExamNode API related urls
urlpatterns += patterns('',
)

# NodeReport API related urls
urlpatterns += patterns('',

)


# NodeValue API related urls
urlpatterns += patterns('',
    (r'^node_value/search/$', node_value.search),
    (r'^node_value/new/$', node_value.new),
    (r'^node_value/bulk_new/$', node_value.bulk_new),
    (r'^node_value/(?P<node_id>\d+)/edit/$', node_value.edit),
    (r'^node_value/bulk_edit/$', node_value.bulk_edit),
    (r'^node_value/(?P<node_id>\d+)/delete/$', node_value.delete),
    (r'^node_value/bulk_edit/$', node_value.bulk_delete),
)