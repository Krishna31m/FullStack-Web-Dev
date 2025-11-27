from django.shortcuts import render
from django.shortcuts import redirect
# Create your views here.
from django.views.generic import TemplateView
from django.contrib.auth.forms import UserCreationForm
from django.views.generic.edit import FormView
from django.urls import reverse_lazy
from django.contrib.auth.views import LoginView as AuthLoginView
from django.contrib.auth.views import LogoutView as AuthLogoutView
from django.contrib.auth.mixins import LoginRequiredMixin

class RegisterView(FormView):
    template_name = "accounts/register.html"
    form_class = UserCreationForm
    success_url = reverse_lazy("login")

    def dispatch(self, request, *args, **kwargs):
        if request.user.is_authenticated:
            return redirect("dashboard")
        return super().dispatch(request, *args, **kwargs)

    def form_valid(self, form):
        form.save()
        return super().form_valid(form)

class LoginView(AuthLoginView):
    template_name = "accounts/login.html"
    redirect_authenticated_user = True

class DashboardView(LoginRequiredMixin, TemplateView):
    template_name = "accounts/dashboard.html"
    login_url = "login"

class LogoutView(AuthLogoutView):
    next_page = "login"