package com.lastpro.taskmate.model

import java.time.LocalDate

data class Task(val id: Int,val tasklabel_id: Int, val title: String, val description: String, val dueDate: LocalDate, val created_at: LocalDate, val updated_at: LocalDate)
