package com.learn.githubusercompose.domain.model

import com.learn.githubusercompose.R

object FakeUserDataSource {
    val dummyUser = listOf(
        User(
            1,
            "Alice",
            "An enthusiastic Android developer.",
            R.drawable.profile_1,
            42,
            100,
            150
        ),
        User(
            2,
            "Bob",
            "Backend engineer with a love for Kotlin.",
            R.drawable.profile_2,
            35,
            85,
            120
        ),
        User(
            3,
            "Charlie",
            "Full-stack developer and tech blogger.",
            R.drawable.profile_3,
            27,
            60,
            90
        ),
        User(
            4,
            "David",
            "iOS developer and UI/UX enthusiast.",
            R.drawable.profile_4,
            12,
            45,
            80
        ),
        User(
            5,
            "Eve",
            "DevOps specialist and automation guru.",
            R.drawable.profile_5,
            50,
            110,
            160
        ),
        User(
            6,
            "Frank",
            "Cybersecurity expert and ethical hacker.",
            R.drawable.profile_6,
            18,
            70,
            95
        ),
        User(
            7,
            "Grace",
            "Cloud architect and data scientist.",
            R.drawable.profile_7,
            22,
            90,
            130
        ),
        User(
            8,
            "Hank",
            "Game developer with a passion for AI.",
            R.drawable.profile_8,
            30,
            75,
            100
        ),
        User(
            9,
            "Ivy",
            "Machine learning engineer and Python lover.",
            R.drawable.profile_9,
            17,
            95,
            140
        ),
        User(
            10,
            "Jack",
            "Frontend developer specializing in React.",
            R.drawable.profile_10,
            29,
            105,
            150
        )
    )
    val dummyFollow = dummyUser.shuffled()
}