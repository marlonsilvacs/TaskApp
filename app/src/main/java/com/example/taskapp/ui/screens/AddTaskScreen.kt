package com.example.taskapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskapp.ui.theme.*

// ─── tela

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onBack: () -> Unit = {},
    onSave: () -> Unit = {}
) {
    var title       by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCat by remember { mutableStateOf("Trabalho") }
    var selectedPri by remember { mutableStateOf(Priority.MEDIUM) }
    var selectedDate by remember { mutableStateOf("Hoje") }
    var selectedTime by remember { mutableStateOf("14:00") }

    val cats = listOf("Trabalho", "Pessoal", "Saúde", "Estudo", "Finanças")
    val dates = listOf("Hoje", "Amanhã", "Esta semana", "Personalizado")

    Box(Modifier.fillMaxSize()) {
        // Degrade de cima
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Brush.verticalGradient(listOf(Indigo800, Indigo900)))
        )

        Column(Modifier.fillMaxSize()) {
            // ── barrinha
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
                Text(
                    "Nova Tarefa",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                // placeholder to balance
                Spacer(Modifier.size(48.dp))
            }

            // ── cartão de formulário
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                // título
                SectionLabel("Título da tarefa")
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ex: Finalizar relatório") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Violet500,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        focusedContainerColor   = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = Violet500)
                    }
                )

                // descrição
                SectionLabel("Descrição (opcional)")
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    placeholder = { Text("Adicione detalhes sobre a tarefa…") },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Violet500,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        focusedContainerColor   = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )

                // Categoria
                SectionLabel("Categoria")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    cats.forEach { cat ->
                        val selected = cat == selectedCat
                        FilterChip(
                            selected = selected,
                            onClick  = { selectedCat = cat },
                            label    = { Text(cat, style = MaterialTheme.typography.bodyMedium) },
                            colors   = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Violet500,
                                selectedLabelColor     = Color.White,
                                containerColor         = MaterialTheme.colorScheme.surfaceVariant,
                                labelColor             = MaterialTheme.colorScheme.onSurface
                            ),
                            shape    = RoundedCornerShape(50)
                        )
                    }
                }

                // Prioridades
                SectionLabel("Prioridade")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Priority.entries.forEach { pri ->
                        PriorityChip(
                            priority = pri,
                            selected = pri == selectedPri,
                            onClick  = { selectedPri = pri },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Data
                SectionLabel("Data de vencimento")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    dates.forEach { d ->
                        val sel = d == selectedDate
                        FilterChip(
                            selected = sel,
                            onClick  = { selectedDate = d },
                            label    = { Text(d, style = MaterialTheme.typography.bodyMedium) },
                            colors   = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Indigo800,
                                selectedLabelColor     = Color.White,
                                containerColor         = MaterialTheme.colorScheme.surfaceVariant,
                                labelColor             = MaterialTheme.colorScheme.onSurface
                            ),
                            shape    = RoundedCornerShape(50)
                        )
                    }
                }

                // Tempo e lembrar
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InfoCard(
                        icon  = Icons.Default.Schedule,
                        label = "Horário",
                        value = selectedTime,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        icon  = Icons.Default.NotificationsNone,
                        label = "Lembrete",
                        value = "30 min antes",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(8.dp))

                // botão de salvar
                Button(
                    onClick = onSave,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape  = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Violet500)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Salvar tarefa", style = MaterialTheme.typography.labelLarge)
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

// ─── Helper composables ───────────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
    )
}

@Composable
private fun PriorityChip(
    priority: Priority,
    selected: Boolean,
    onClick:  () -> Unit,
    modifier: Modifier = Modifier
) {
    val border = if (selected) Modifier.border(2.dp, priority.color, RoundedCornerShape(12.dp)) else Modifier
    Surface(
        modifier  = modifier
            .then(border)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        color     = if (selected) priority.color.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant,
        shape     = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(priority.color)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                priority.label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (selected) priority.color else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun InfoCard(
    icon:     ImageVector,
    label:    String,
    value:    String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape    = RoundedCornerShape(14.dp),
        color    = MaterialTheme.colorScheme.surfaceVariant,
        onClick  = {}
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = Violet500, modifier = Modifier.size(20.dp))
            Column {
                Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                Text(value, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

// ─── Preview ──────────────────────────────────────────────────────────────────

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun AddTaskScreenPreview() {
    com.example.taskapp.ui.theme.TaskAppTheme {
        AddTaskScreen()
    }
}
