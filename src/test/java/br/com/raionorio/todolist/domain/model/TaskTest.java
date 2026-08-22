package br.com.raionorio.todolist.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskTest {

    @Test
    void shouldCreateTaskWithPendingStatus() {
        Task task = Task.create(
                "Estudar Java",
                "Revisar orientação a objetos",
                TaskPriority.HIGH,
                LocalDate.now().plusDays(1)
        );

        assertEquals(TaskStatus.PENDING, task.getStatus());
    }
    @Test
    void shouldRejectBlankTitle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Task.create(
                        "",
                        "Descrição válida",
                        TaskPriority.MEDIUM,
                        LocalDate.now() .plusDays(1)
                        )
        );
    }
    @Test
    void shouldRejectTitleLongerThan120Characters() {
        String title = "a".repeat(121);

        assertThrows(
                IllegalArgumentException.class,
                () -> Task.create(
                        title,
                        "Descrição válida",
                        TaskPriority.MEDIUM,
                        LocalDate.now().plusDays(1)
                )
        );
    }
    @Test
    void shouldRejectDescriptionLongerThan500Characters() {
        String description = "a".repeat(501);

        assertThrows(
                IllegalArgumentException.class,
                () -> Task.create(
                        "Título válido",
                        description,
                        TaskPriority.MEDIUM,
                        LocalDate.now().plusDays(1)
                )
        );
    }
    @Test
    void shouldStartPendingTask() {
        Task task = Task.create(
                "Estudar Spring Boot",
                "Continuar projeto do portfólio",
                TaskPriority.HIGH,
                LocalDate.now().plusDays(1)
        );

        task.start();

        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }
    @Test
    void shouldCompleteTaskInProgress() {
        Task task = Task.create(
                "Finalizar testes",
                "Validar conclusão da tarefa",
                TaskPriority.HIGH,
                LocalDate.now().plusDays(1)
        );

        task.start();
        task.complete();

        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }
    @Test
    void shouldNotCompletePendingTask() {
        Task task = Task.create(
                "Concluir tarefa",
                "Testar transição inválida",
                TaskPriority.HIGH,
                LocalDate.now().plusDays(1)
        );

        assertThrows(
                IllegalStateException.class,
                task::complete
        );
    }
    @Test
    void shouldNotCancelCompletedTask() {
        Task task = Task.create(
                "Finalizar tarefa",
                "Testar cancelamento após conclusão",
                TaskPriority.MEDIUM,
                LocalDate.now().plusDays(1)
        );

        task.start();
        task.complete();

        assertThrows(
                IllegalStateException.class,
                task::cancel
        );
    }
}