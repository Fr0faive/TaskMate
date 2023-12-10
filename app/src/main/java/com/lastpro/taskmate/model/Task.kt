package com.lastpro.taskmate.model

import java.time.LocalDate

data class Task(val task_id: Int, val title: String, val description: String, val dueDate: LocalDate, val createdAt: LocalDate, val updatedAt: LocalDate)
