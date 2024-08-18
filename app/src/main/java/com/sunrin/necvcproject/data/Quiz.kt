package com.sunrin.necvcproject.data

val QuizList = listOf(
    "내가 헛되이 보낸 오늘 하루는 어제 죽어간 이들이 그토록 바라던 하루이다. 단 하루면 인간적인 모든 것을 멸망시킬 수 있고 다시 소생시킬 수도 있다.",

    "우리는 1년 후면 다 잊어버릴 슬픔을 간직하느라, 무엇과도 바꿀 수 없는 소중한 시간을 낭비하고 있다. 소심하게 굴기에 인생은 너무나 짧습니다.",

    "변명 중에서도 가장 어리석고 못난 변명은 '시간이 없어서'라는 변명이다.",

    "시간을 지배할 줄 아는 사람은 인생을 지배할 줄 아는 사람이다.",

    "시간의 걸음걸이에는 세 가지가 있다. 미래는 주저하면서 다가오고, 현재는 화살처럼 날아가고, 과거는 영원히 정지하고 있다.",

    "짧은 인생은 시간 낭비에 의해 더욱 짧아진다.",

    "시간을 단축시키는 것은 활동이요, 시간을 견디지 못하게 하는 것은 안일함이다.",

    "미래를 신뢰하지 마라, 죽은 과거는 묻어버려라, 그리고 살아있는 현재에 행동하라.",

    "오늘 하루를 헛되이 보냈다면 그것은 커다란 손실이다. 하루를 유익하게 보낸 사람은 하루의 보물을 파낸 것이다. 하루를 헛되이 보낸 내 몸을 헛되이 소모하고 있다는 것을 기억해야 한다."
)

val wordsToReplace = listOf(
    "하루",
    "슬픔",
    "시간",
    "시간",
    "시간",
    "인생",
    "시간",
    "미래",
    "하루"
)

val modifiedQuizList = QuizList.mapIndexed { index, quote ->
    val word = wordsToReplace[index]
    val modifiedQuote = quote.replace(word, "○".repeat(word.length))
    Triple(index, word, modifiedQuote)
}
