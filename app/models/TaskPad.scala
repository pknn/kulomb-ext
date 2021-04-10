package models

import java.sql.Timestamp

case class TaskPad(id: String,
                   task: Task,
                   creator: User,
                   accessKey: String,
                   secretKey: String,
                   createdAt: Timestamp,
                   updatedAt: Timestamp)