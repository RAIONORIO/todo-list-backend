package br.com.raionorio.todolist.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Task {

    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Task(
            UUID id,
            String title,
            String description,
            TaskStatus status,
            TaskPriority priority,
            LocalDate dueDate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Task create(
            String title,
            String description,
            TaskPriority priority,
            LocalDate dueDate
    ) {
        validateTitle(title);
        validateDescription(description);

        LocalDateTime now = LocalDateTime.now();

        return new Task(
                UUID.randomUUID(),
                title.trim(),
                description,
                TaskStatus.PENDING,
                priority,
                dueDate,
                now,
                now
        );
    }

    public void start() {
        if (status != TaskStatus.PENDING) {
            throw new IllegalStateException(
                    "Somente tarefas pendentes podem ser iniciadas."
            );
        }

        status = TaskStatus.IN_PROGRESS;
        touch();
    }

    public void complete() {
        if (status != TaskStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Somente tarefas em andamento podem ser concluídas."
            );
        }

        status = TaskStatus.COMPLETED;
        touch();
    }

    public void cancel() {
        if (status == TaskStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Uma tarefa concluída não pode ser cancelada."
            );
        }

        if (status == TaskStatus.CANCELED) {
            throw new IllegalStateException(
                    "A tarefa já está cancelada."
            );
        }

        status = TaskStatus.CANCELED;
        touch();
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "O título da tarefa é obrigatório."
            );
        }

        if (title.trim().length() > 120) {
            throw new IllegalArgumentException(
                    "O título da tarefa deve ter no máximo 120 caracteres."
            );
        }
    }

    private static void validateDescription(String description) {
        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException(
                    "A descrição da tarefa deve ter no máximo 500 caracteres."
            );
        }
    }

    private void touch() {
        updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
