package com.example.taskapp.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskapp.ui.theme.*

// ─── Dados exemplos

data class Task(
    val id: Int,
    val title: String,
    val subtitle: String,
    val category: String,
    val priority: Priority,
    val isCompleted: Boolean = false,
    val dueTime: String = ""
)

enum class Priority(val color: Color, val label: String) {
    HIGH(Rose500, "Alta"),
    MEDIUM(Amber400, "Média"),
    LOW(Mint400, "Baixa")
}

val fakeTasks = listOf(
    Task(1, "Revisar relatório mensal", "Análise de KPIs do trimestre", "Trabalho", Priority.HIGH, false, "14:00"),
    Task(2, "Comprar mantimentos", "Frutas, legumes e proteínas", "Pessoal", Priority.LOW, true, "10:00"),
    Task(3, "Reunião com cliente", "Apresentar proposta nova", "Trabalho", Priority.HIGH, false, "16:30"),
    Task(4, "Academia", "Treino de força — pernas", "Saúde", Priority.MEDIUM, false, "07:00"),
    Task(5, "Estudar Kotlin Coroutines", "Cap. 4 do livro Coroutines", "Estudo", Priority.MEDIUM, false, "20:00"),
    Task(6, "Ligar para mamãe", "Confirmar almoço de domingo", "Pessoal", Priority.LOW, true, "12:00"),
)

val categories = listOf("Todas", "Trabalho", "Pessoal", "Saúde", "Estudo")

// ─── Tela 

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddTask: () -> Unit = {},
    onTaskClick: (Int) -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf("Todas") }
    val tasks by remember { mutableStateOf(fakeTasks) }

    val filtered = if (selectedCategory == "Todas") tasks
    else tasks.filter { it.category == selectedCategory }

    val done  = tasks.count { it.isCompleted }
    val total = tasks.size
    val progress = if (total > 0) done.toFloat() / total else 0f

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTask,
                containerColor = Violet500,
                contentColor   = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nova tarefa", modifier = Modifier.size(28.dp))
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 88.dp)
        ) {
            // ──  Cabecalho
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(listOf(Indigo800, Indigo900))
                        )
                        .padding(horizontal = 24.dp, vertical = 28.dp)
                ) {
                    Column {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    "Bom dia! 👋",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Violet400
                                )
                                Text(
                                    "Suas tarefas",
                                    style = MaterialTheme.typography.displayLarge,
                                    color = Color.White
                                )
                            }
                            // Avatar
                            Box(
                                Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Violet500),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("JS", style = MaterialTheme.typography.titleMedium, color = Color.White)
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        // Telinha de progresso
                        Surface(
                            shape  = RoundedCornerShape(16.dp),
                            color  = Indigo600.copy(alpha = 0.6f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "$done de $total tarefas concluídas",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = OnDark.copy(alpha = 0.8f)
                                    )
                                    Text(
                                        "${(progress * 100).toInt()}%",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = Mint400
                                    )
                                }
                                Spacer(Modifier.height(10.dp))
                                LinearProgressIndicator(
                                    progress = { progress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color    = Mint400,
                                    trackColor = Indigo900.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                }
            }

            // ── Filtro de categoria
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val selected = cat == selectedCategory
                        FilterChip(
                            selected = selected,
                            onClick  = { selectedCategory = cat },
                            label    = { Text(cat) },
                            colors   = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Violet500,
                                selectedLabelColor     = Color.White,
                                containerColor         = MaterialTheme.colorScheme.surfaceVariant,
                                labelColor             = MaterialTheme.colorScheme.onSurface
                            ),
                            shape = RoundedCornerShape(50)
                        )
                    }
                }
            }

            // ── pendencias
            item {
                Text(
                    "Pendentes · ${filtered.count { !it.isCompleted }}",
                    style  = MaterialTheme.typography.labelLarge,
                    color  = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                )
            }

            // ── Itens de tarefas
            items(filtered) { task ->
                TaskItem(
                    task     = task,
                    onClick  = { onTaskClick(task.id) }
                )
            }
        }
    }
}

// ─── item de tarefas

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (task.isCompleted)
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        else
            MaterialTheme.colorScheme.surface,
        animationSpec = tween(300), label = "bg"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = bgColor,
        shadowElevation = if (task.isCompleted) 0.dp else 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circulo verificado
            Icon(
                imageVector = if (task.isCompleted) Icons.Outlined.CheckCircle
                              else Icons.Outlined.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (task.isCompleted) Mint400 else MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(26.dp)
            )

            Spacer(Modifier.width(14.dp))

            // Conteúdo
            Column(Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (task.isCompleted)
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    else
                        MaterialTheme.colorScheme.onSurface,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(3.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        task.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(horizontalAlignment = Alignment.End) {
                // ponto de prioridade
                Box(
                    Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(task.priority.color)
                )
                Spacer(Modifier.height(6.dp))
                if (task.dueTime.isNotEmpty()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = null,
                            tint   = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            task.dueTime,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = androidx.compose.ui.unit.TextUnit(11f, androidx.compose.ui.unit.TextUnitType.Sp)),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    }
                }
            }
        }
    }
}

// ─── Preview 
@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun HomeScreenPreview() {
    com.example.taskapp.ui.theme.TaskAppTheme {
        HomeScreen()
    }
}
