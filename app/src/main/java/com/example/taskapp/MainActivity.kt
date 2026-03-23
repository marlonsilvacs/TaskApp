package com.example.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskapp.ui.screens.AddTaskScreen
import com.example.taskapp.ui.screens.HomeScreen
import com.example.taskapp.ui.screens.TaskDetailScreen
import com.example.taskapp.ui.theme.TaskAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(
                        navController    = navController,
                        startDestination = Screen.Home.route
                    ) {
                        // ── Tela 1: Home / Lista de tarefas ───────────────────
                        composable(Screen.Home.route) {
                            HomeScreen(
                                onAddTask  = { navController.navigate(Screen.AddTask.route) },
                                onTaskClick = { id ->
                                    navController.navigate(Screen.TaskDetail.createRoute(id))
                                }
                            )
                        }

                        // ── Tela 2: Adicionar tarefa ──────────────────────────
                        composable(Screen.AddTask.route) {
                            AddTaskScreen(
                                onBack = { navController.popBackStack() },
                                onSave = { navController.popBackStack() }
                            )
                        }

                        // ── Tela 3: Detalhes da tarefa ────────────────────────
                        composable(
                            route = Screen.TaskDetail.route,
                            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 1
                            TaskDetailScreen(
                                taskId = taskId,
                                onBack = { navController.popBackStack() },
                                onEdit = { navController.navigate(Screen.AddTask.route) }
                            )
                        }
                    }
                }
            }
        }
    }
}
