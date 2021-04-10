package models

import commons.Types.Version

case class Task(id: String,
                name: String,
                note: String,
                source: String,
                language: String,
                languageOptions: Seq[String],
                htmlTemplate: String,
                code: Code,
                disabled: Boolean,
                isPrivate: Boolean,
                tags: Seq[String],
                generator: String,
                textGrader: String,
                testCases: String,
                textBlanks: String,
                creator: User,
                owner: User,
                version: Version,
                previousVersion: Version)



