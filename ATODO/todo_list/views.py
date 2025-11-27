from django.shortcuts import render

# Create your views here.
from django.views.generic import ListView, UpdateView, DeleteView, CreateView
from .models import Todo
from django.urls import reverse_lazy
from django.contrib.auth.mixins import LoginRequiredMixin


class TodoListView(LoginRequiredMixin,ListView):
    model = Todo
    template_name = 'todo_list/list.html'
    context_object_name = "todos"


class TodoCreateView(CreateView):
    model = Todo
    template_name = "todo_list/create.html"
    fields = ['title', 'description', 'is_active']
    success_url = reverse_lazy("todo_list")

class TodoUpdateView(UpdateView):
    model = Todo
    template_name = "todo_list/update.html"
    fields = ['title', 'description', 'is_active']
    success_url = reverse_lazy("todo_list")

class TodoDeleteView(DeleteView):
    model = Todo
    template_name = "todo_list/delete.html"
    success_url = reverse_lazy("todo_list")