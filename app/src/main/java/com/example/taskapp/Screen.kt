package com.example.taskapp

sealed class Screen(val route: String) {
    object Home       : Screen("home")
    object AddTask    : Screen("add_task")
    object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: Int) = "task_detail/$taskId"
    }
}
