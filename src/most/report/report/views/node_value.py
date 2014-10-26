from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_POST, require_GET
from django.utils.translation import ugettext as _
import json
from datetime import date, datetime
from django.db.models import Q
from django.contrib.auth.decorators import login_required, user_passes_test
from most.report.report.views import SUCCESS_KEY, MESSAGE_KEY, TOTAL_KEY, ERRORS_KEY, DATA_KEY
from most.web.users.models import ClinicianUser
from most.web.report.models import ExamNode, NodeReport, NodeValue
from most.report.report.forms import ExamNodeForm


def create_node_value(attributes):
    try:
        exam_node_value_form = ExamNodeForm(attributes)
        if exam_node_value_form.is_valid():
            exam_node_value = exam_node_value_form.save(commit=False)
            return True, exam_node_value
        else:
            errors = ''
            for field, error in exam_node_value_form.errors.items():
                errors += '\n%s\n' % error
            return False, errors
    except Exception, e:
        return False, e


def bulk_create_node_value(nodes_attributes):
    passed_nodes = []
    errors = ''
    for node_attributes in nodes_attributes:
        try:
            passed, node_instance = create_node_value(node_attributes)
            if passed:
                passed_nodes.append(node_instance)
            else:
                errors += '%s\n' % node_instance
        except Exception, e:
            errors += '%s\n' % e
    return passed_nodes if passed_nodes else None, errors if errors else None


def edit_node_value(node_id, attributes):
    try:
        exam_node_value = NodeValue.objects.get(pk=node_id)
        exam_node_value_form = ExamNodeForm(attributes, instance=exam_node_value)
        if exam_node_value_form.is_valid():
            exam_node_value = exam_node_value_form.save(commit=False)
            return True, exam_node_value
        else:
            errors = ''
            for field, error in exam_node_value_form.errors.items():
                errors += '\n%s\n' % error
            return False, errors
    except Exception, e:
        return False, e


def bulk_edit_node_value(**nodes_attributes):
    passed_nodes = []
    errors = ''
    for node_id in nodes_attributes:
        try:
            passed, node_instance = edit_node_value(node_id, nodes_attributes[node_id])
            if passed:
                passed_nodes.append(node_instance)
            else:
                errors += '%s\n' % node_instance
        except Exception, e:
            errors += '%s\n' % e
    return passed_nodes if passed_nodes else None, errors if errors else None


def delete_node_value(node_id):
    try:
        node_value = NodeValue.objects.filter(id=node_id).delete()
        return True, node_value.pk
    except Exception, e:
        return False, e


def bulk_delete_node_value(*nodes):
    passed_nodes = []
    errors = ''
    for node_id in nodes:
        try:
            passed, node_id = delete_node_value(node_id)
            if passed:
                passed_nodes.append(node_id)
            else:
                errors += '%s\n' % node_id
        except Exception, e:
            errors += '%s\n' % e
    return passed_nodes if passed_nodes else None, errors if errors else None

@csrf_exempt
@require_POST
@login_required
def new(request):
    results = {}
    try:
        results[SUCCESS_KEY], exam_node_value = create_node_value(request.POST)
        if results[SUCCESS_KEY]:
            exam_node_value.save()
            results[MESSAGE_KEY] = _('Node value %s successfully created.' % exam_node_value.pk)
            results[DATA_KEY] = exam_node_value.to_dictionary()
        else:
            results[ERRORS_KEY] = _('Unable to create node value.')
    except Exception, e:
        results[SUCCESS_KEY] = False
        results[ERRORS_KEY] = e
    return HttpResponse(json.dumps(results), content_type='application/json; charset=utf8')


@csrf_exempt
@require_POST
@login_required
def bulk_new(request):
    results = {}
    try:
        passed_nodes, results[ERRORS_KEY] = bulk_create_node_value(json.loads(request.body))
        if not results[ERRORS_KEY]:
            results[DATA_KEY] = []
            for node in passed_nodes:
                results[DATA_KEY].append(node.save())
            results[SUCCESS_KEY] = True
            results[MESSAGE_KEY] = _('Node values successfully created.')
        else:
            results[ERRORS_KEY] = _('Unable to create node values.')
    except Exception, e:
        results[SUCCESS_KEY] = False
        results[ERRORS_KEY] = e
    return HttpResponse(json.dumps(results), content_type='application/json; charset=utf8')


@csrf_exempt
@require_POST
@login_required
def edit(request, node_value_id):
    results = {}
    try:
        results[SUCCESS_KEY], exam_node_value = edit_node_value(node_value_id, request.POST)
        if results[SUCCESS_KEY]:
            exam_node_value.save()
            results[MESSAGE_KEY] = _('Node value %s successfully updated.' % exam_node_value.pk)
            results[DATA_KEY] = exam_node_value.to_dictionary()
        else:
            results[ERRORS_KEY] = _('Unable to update node value %s.' % exam_node_value.pk)
    except Exception, e:
        results[SUCCESS_KEY] = False
        results[ERRORS_KEY] = e
    return HttpResponse(json.dumps(results), content_type='application/json; charset=utf8')


@csrf_exempt
@require_POST
@login_required
def bulk_edit(request):
    # mi aspetto in ingresso nella post un JSON: un dizionario dove nella chiave si trova l'id del nodo e come valore
    # un altro dizionario con attributo e valore da assegnare al nodo
    results = {}
    try:
        passed_nodes, results[ERRORS_KEY] = bulk_edit_node_value(json.loads(request.body))
        if not results[ERRORS_KEY]:
            results[DATA_KEY] = []
            for node in passed_nodes:
                results[DATA_KEY].append(node.save())
            results[SUCCESS_KEY] = True
            results[MESSAGE_KEY] = _('Node values successfully updated.')
        else:
            results[ERRORS_KEY] = _('Unable to update node values.')
    except Exception, e:
        results[SUCCESS_KEY] = False
        results[ERRORS_KEY] = e
    return HttpResponse(json.dumps(results), content_type='application/json; charset=utf8')


@csrf_exempt
@require_POST
@login_required
def delete(request, node_value_id):
    results = {}
    try:
        results[SUCCESS_KEY], exam_node_value_id = delete_node_value(node_value_id)
        if results[SUCCESS_KEY]:
            results[MESSAGE_KEY] = _('Node value %s successfully deleted.' % exam_node_value_id)
            results[DATA_KEY] = {'node_value_id': exam_node_value_id}
        else:
            results[ERRORS_KEY] = _('Unable to delete node value %s.' % exam_node_value_id)
    except Exception, e:
        results[SUCCESS_KEY] = False
        results[ERRORS_KEY] = e
    return HttpResponse(json.dumps(results), content_type='application/json; charset=utf8')


@csrf_exempt
@require_POST
@login_required
def bulk_delete(request):
    # request_body = json :: lista di id di node_value da eliminare
    results = {}
    try:
        results[DATA_KEY], results[ERRORS_KEY] = bulk_delete_node_value(json.loads(request.body))
        if not results[ERRORS_KEY]:
            results[SUCCESS_KEY] = True
            results[MESSAGE_KEY] = _('Node values successfully deleted.')
        else:
            results[ERRORS_KEY] = _('Unable to delete node values.')
    except Exception, e:
        results[SUCCESS_KEY] = False
        results[ERRORS_KEY] = e
    return HttpResponse(json.dumps(results), content_type='application/json; charset=utf8')


@login_required
def get_info(request, node_value_id):
    results = {}
    try:
        node_value = NodeValue.objects.get(pk=node_value_id)
        results[SUCCESS_KEY] = True
        results[MESSAGE_KEY] = _('Node value %s found.' % node_value_id)
        results[DATA_KEY] = node_value.to_dictionary()
    except Exception, e:
        results[SUCCESS_KEY] = False
        results[ERRORS_KEY] = e
    return HttpResponse(json.dumps(results), content_type='application/json; charset=utf8')


@login_required
def get_label_value(request, node_value_id):
    results = {}
    try:
        node_value = NodeValue.objects.get(pk=node_value_id)
        results[SUCCESS_KEY] = True
        results[MESSAGE_KEY] = _('Node value %s found.' % node_value_id)
        results[DATA_KEY] = node_value.to_label_value()
    except Exception, e:
        results[SUCCESS_KEY] = False
        results[ERRORS_KEY] = e
    return HttpResponse(json.dumps(results), content_type='application/json; charset=utf8')
