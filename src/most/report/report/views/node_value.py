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
            exam_node_value = exam_node_value_form.save()
            return True, exam_node_value
        else:
            errors = ''
            for field, error in exam_node_value_form.errors.items():
                errors += '\n%s\n' % error
            return False, errors
    except Exception, e:
        return False, e


def delete_node_value(node_id):
    try:
        node_value = NodeValue.objects.filter(id=node_id).delete()
        return True, node_value
    except Exception, e:
        return False, e


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
            results[ERRORS_KEY] = _('Unable to create section. %s' % exam_node_value)
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
        passed_nodes, results[ERRORS_KEY] = bulk_create_node_value(request.POST)
        if not results[ERRORS_KEY]:
            for node in passed_nodes:
                node.save()
            results[SUCCESS_KEY] = True
            results[MESSAGE_KEY] = _('Node value %s successfully created.' % exam_node_value.pk)
            results[DATA_KEY] = exam_node_value.to_dictionary()
        else:
            results[ERRORS_KEY] = _('Unable to create section. %s' % exam_node_value)
    except Exception, e:
        results[SUCCESS_KEY] = False
        results[ERRORS_KEY] = e
    return HttpResponse(json.dumps(results), content_type='application/json; charset=utf8')



@csrf_exempt
@require_POST
@login_required
def edit(request, node_value_id):
    pass


@csrf_exempt
@require_POST
@login_required
def bulk_edit(request):
    pass


@csrf_exempt
@require_POST
@login_required
def delete(request, node_value_id):
    pass


@csrf_exempt
@require_POST
@login_required
def bulk_delete(request):
    pass