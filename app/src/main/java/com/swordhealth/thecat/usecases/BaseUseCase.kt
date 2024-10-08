package com.swordhealth.thecat.usecases

interface BaseUseCase<in Input, out Output> {
    suspend fun execute(input: Input): Output
}