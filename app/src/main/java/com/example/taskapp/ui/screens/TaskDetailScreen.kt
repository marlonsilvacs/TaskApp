package com.example.taskapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskapp.ui.theme.*

// ─── Screen ───────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Int = 1,
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {}
) {
    // Fake task data selected by id
    val task = fakeTasks.find { it.id == taskId } ?: fakeTasks.first()

    var isCompleted by remember { mutableStateOf(task.isCompleted) }

    val subtasks = remember {
        mutableStateListOf(
            SubTask(1, "Coletar dados do mês anterior", true),
            SubTask(2, "Comparar com metas estipuladas", true),
            SubTask(3, "Montar apresentação de slides", false),
            SubTask(4, "Enviar para o time até sexta", false),
        )
    }

    val completedSub = subtasks.count { it.done }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.White)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Mais opções", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // ── Colored header ───────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(Indigo800, Indigo900)))
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp, top = 4.dp)
            ) {
                Column {
                    // Category badge
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Violet500.copy(alpha = 0.25f)
                    ) {
                        Text(
                            task.category,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            style    = MaterialTheme.typography.labelLarge,
                            color    = Violet400
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        task.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        textDecoration = if (isCompleted) TextDecoration.LineThrough else null
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        task.subtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = OnDark.copy(alpha = 0.65f)
                    )

                    Spacer(Modifier.height(20.dp))

                    // Meta row
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        MetaBadge(Icons.Default.Schedule,  task.dueTime.ifEmpty { "Sem hora" })
                        MetaBadge(Icons.Default.FiberManualRecord, task.priority.label, task.priority.color)
                        if (isCompleted)
                            MetaBadge(Icons.Outlined.CheckCircle, "Concluída", Mint400)
                    }
                }
            }

            // ── Body card ────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {

                // ── Status toggle ─────────────────────────────────────────────
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isCompleted) Mint400.copy(alpha = 0.1f)
                            else MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                if (isCompleted) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = if (isCompleted) Mint400 else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                modifier = Modifier.size(24.dp)
                            )
                            Column {
                                Text(
                                    if (isCompleted) "Tarefa concluída!" else "Marcar como concluída",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = if (isCompleted) Mint400 else MaterialTheme.colorScheme.onSurface
                                )
                                if (isCompleted)
                                    Text(
                                        "Parabéns 🎉",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Mint400.copy(alpha = 0.7f)
                                    )
                            }
                        }
                        Switch(
                            checked = isCompleted,
                            onCheckedChange = { isCompleted = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor      = Color.White,
                                checkedTrackColor      = Mint400,
                                uncheckedThumbColor    = MaterialTheme.colorScheme.outline,
                                uncheckedTrackColor    = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            )
                        )
                    }
                }

                // ── Subtasks ───────────────────────────────────────────────────
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text(
                            "Subtarefas",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            "$completedSub/${subtasks.size}",
                            style = MaterialTheme.typography.labelLarge,
                            color = Violet500
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { if (subtasks.isEmpty()) 0f else completedSub.toFloat() / subtasks.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color      = Violet500,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Spacer(Modifier.height(12.dp))
                    subtasks.forEachIndexed { index, sub ->
                        SubtaskRow(
                            subtask  = sub,
                            onToggle = { subtasks[index] = sub.copy(done = !sub.done) }
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    TextButton(
                        onClick = {},
                        modifier = Modifier.align(Alignment.Start)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Violet500, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Adicionar subtarefa", color = Violet500)
                    }
                }

                // ── Info grid ─────────────────────────────────────────────────
                Text("Detalhes", style = MaterialTheme.typography.titleMedium)
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    DetailRow(Icons.Default.CalendarToday, "Data", "Hoje, ${task.dueTime}")
                    DetailRow(Icons.Default.NotificationsNone, "Lembrete", "30 min antes")
                    DetailRow(Icons.Default.Label, "Categoria", task.category)
                    DetailRow(Icons.Default.FiberManualRecord, "Prioridade", task.priority.label)
                }

                // ── Delete ────────────────────────────────────────────────────
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape  = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Rose500),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Rose500.copy(alpha = 0.4f))
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Excluir tarefa")
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

// ─── Helper composables ───────────────────────────────────────────────────────

data class SubTask(val id: Int, val title: String, val done: Boolean)

@Composable
private fun MetaBadge(icon: ImageVector, label: String, iconTint: Color = OnDark.copy(alpha = 0.6f)) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(14.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium, color = OnDark.copy(alpha = 0.7f))
    }
}

@Composable
private fun SubtaskRow(subtask: SubTask, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Checkbox(
            checked = subtask.done,
            onCheckedChange = { onToggle() },
            colors = CheckboxDefaults.colors(
                checkedColor   = Mint400,
                checkmarkColor = Indigo900
            )
        )
        Text(
            subtask.title,
            style = MaterialTheme.typography.bodyLarge,
            color = if (subtask.done) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    else MaterialTheme.colorScheme.onSurface,
            textDecoration = if (subtask.done) TextDecoration.LineThrough else null
        )
    }
}

@Composable
private fun DetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Violet500, modifier = Modifier.size(18.dp))
        }
        Column {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f))
            Text(value, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

// ─── Preview ──────────────────────────────────────────────────────────────────

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun TaskDetailScreenPreview() {
    com.example.taskapp.ui.theme.TaskAppTheme {
        TaskDetailScreen()
    }
}
