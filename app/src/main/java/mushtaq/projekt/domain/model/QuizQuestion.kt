package mushtaq.projekt.domain.model

data class QuizQuestion(
    val id: Int,
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val topic: String
)
