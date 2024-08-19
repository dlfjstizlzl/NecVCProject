package com.sunrin.necvcproject.data

private val SentenceList = listOf(
    "시간은 금이다.",
    "오늘 할 일을 내일로 미루지 마라.",
    "시간은 되돌릴 수 없는 강물이다.",
    "시간은 당신이 가진 유일한 자본이다.",
    "현재를 사랑하라. 현재가 곧 인생이다.",
    "시간을 단축시키는 것은 활동이요, 시간을 견디지 못하게 하는 것은 안일이다.",
    "시간은 우리가 갖고 있는 가장 가치 있는 것이다.",
    "잃어버린 돈은 찾을 수 있어도 잃어버린 시간은 영원히 찾을 수 없다.",
    "시간을 지배할 줄 아는 사람은 인생을 지배할 줄 아는 사람이다.",
    "어제는 이미 가고 내일은 아직 오지 않았다. 우리에게는 오직 오늘이 있을 뿐이다.",
    "시간은 우리가 쓰는 대로 길어지기도 하고 짧아지기도 한다.",
    "당신의 시간은 제한되어 있다. 그러니 다른 사람의 인생을 사느라 시간을 낭비하지 마라.",
    "시간은 인생의 재료이다.",
    "시간을 선택하는 것은 시간을 절약하는 것이다.",
    "시간을 존중하라. 그러면 시간도 당신을 존중할 것이다.",
    "시간은 가장 공평한 것이다. 모든 사람에게 하루 24시간을 준다.",
    "지금이 바로 네가 기다리던 그 순간이다.",
    "시간을 죽이는 자는 시간에 의해 죽임을 당할 것이다.",
    "인생은 짧고 예술은 길다.",
    "시간은 우리가 변화할 수 있는 것이 아니라, 우리를 변화시키는 것이다.",
)

private val AnswerList = listOf(
    "금",
    "내일",
    "강물",
    "자본",
    "현재",
    "안일",
    "가치",
    "시간",
    "인생",
    "오늘",
    "쓰는",
    "낭비",
    "재료",
    "절약",
    "존중",
    "공평",
    "기다리던",
    "죽임",
    "예술",
    "변화"
)

data class Quiz(
    val word: String,
    val sentence: String,
    val maskedSentence: String
)

val ProcessedQuizList = SentenceList.mapIndexed { index, sentence ->
    val word = AnswerList[index]
    val maskedSentence = sentence.replace(word, "○".repeat(word.length))
    Quiz(word, sentence, maskedSentence)
}