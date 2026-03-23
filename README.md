# TaskApp — Aplicativo de Conclusão de Tarefas
### Kotlin + Jetpack Compose · Android Studio

---

## 📱 Telas implementadas

### 1. `HomeScreen` — Lista de Tarefas
- Header com gradiente escuro e saudação personalizada
- Card de progresso com barra animada (tarefas concluídas / total)
- Filtro horizontal por categoria (Todas, Trabalho, Pessoal, Saúde, Estudo)
- Lista de tarefas com:
  - Ícone de check circular (concluída / pendente)
  - Indicador de prioridade (ponto colorido)
  - Horário de vencimento
  - Animação de cor ao concluir
- FAB para adicionar nova tarefa

### 2. `AddTaskScreen` — Nova Tarefa
- Header com gradiente e bottom sheet em card arredondado
- Campo de título e descrição estilizados
- Seleção de categoria via chips
- Seleção de prioridade: Alta / Média / Baixa com cores
- Seleção de data (Hoje / Amanhã / Esta semana / Personalizado)
- Cards de horário e lembrete
- Botão de salvar com ícone

### 3. `TaskDetailScreen` — Detalhes da Tarefa
- Header imersivo com título, categoria, subtítulo e badges de meta
- Toggle Switch para marcar tarefa como concluída
- Lista de subtarefas com checkboxes individuais + barra de progresso
- Seção de detalhes (data, lembrete, categoria, prioridade) com ícones
- Botão de excluir tarefa

---

## 🚀 Como abrir no Android Studio

1. **Clone ou extraia** este projeto em uma pasta local
2. Abra o **Android Studio** (Hedgehog ou mais novo)
3. Vá em **File → Open** e selecione a pasta `TaskApp`
4. Aguarde o **Gradle sync** terminar
5. Execute em um emulador ou dispositivo com **API 26+**

---

## 🏗️ Estrutura do projeto

```
app/src/main/java/com/example/taskapp/
├── MainActivity.kt              ← Navegação (NavHost)
├── Screen.kt                    ← Rotas de navegação
└── ui/
    ├── theme/
    │   └── Theme.kt             ← Cores, tipografia, tema
    └── screens/
        ├── HomeScreen.kt        ← Tela 1: lista de tarefas
        ├── AddTaskScreen.kt     ← Tela 2: nova tarefa
        └── TaskDetailScreen.kt  ← Tela 3: detalhes
```

---

## 🎨 Design System

| Token | Valor |
|---|---|
| Primário | Violet #7C3AED |
| Acento | Mint #34D399 |
| Background escuro | Indigo #1A1040 |
| Erro / Excluir | Rose #F43F5E |
| Prioridade Alta | Rose #F43F5E |
| Prioridade Média | Amber #FBBF24 |
| Prioridade Baixa | Mint #34D399 |

---

## 📦 Dependências principais

- `androidx.navigation:navigation-compose:2.8.5`
- `androidx.compose.material:material-icons-extended`
- `androidx.compose:compose-bom:2024.04.01`
- `androidx.activity:activity-compose:1.10.0`
