from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_POST, require_GET
from django.utils.translation import ugettext as _
import json
from datetime import date, datetime
from django.db.models import Q
from django.contrib.auth.decorators import login_required, user_passes_test
from most.web.exams.views import SUCCESS_KEY, MESSAGE_KEY, TOTAL_KEY, ERRORS_KEY, DATA_KEY
from most.web.exams.forms import ExamForm

@csrf_exempt
@require_POST
@login_required
def new(request):
    pass


@csrf_exempt
@require_POST
@login_required
def bulk_new(request):
    pass


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