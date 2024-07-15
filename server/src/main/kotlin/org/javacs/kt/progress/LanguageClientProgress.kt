package org.javacs.kt.progress

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.LanguageClient
import java.util.*
import java.util.concurrent.CompletableFuture

class LanguageClientProgress(
    private val label: String,
    private val token: Either<String, Int>,
    private val client: LanguageClient
) : Progress {
    init {
        reportProgress(WorkDoneProgressBegin().also {
            it.title = "Kotlin: $label"
            it.percentage = 0
        })
    }

    override fun update(message: String?, percent: Int?) {
        reportProgress(WorkDoneProgressReport().also {
            it.message = message
            it.percentage = percent
        })
    }

    override fun close() {
        reportProgress(WorkDoneProgressEnd())
    }

    private fun reportProgress(notification: WorkDoneProgressNotification) {
        client.notifyProgress(ProgressParams(token, Either.forLeft(notification)))
    }

    class Factory(private val client: LanguageClient) : Progress.Factory {
        override fun create(label: String): CompletableFuture<Progress> {
            val token = Either.forLeft<String, Int>(UUID.randomUUID().toString())
            return client
                .createProgress(WorkDoneProgressCreateParams().also {
                    it.token = token
                })
                .thenApply { LanguageClientProgress(label, token, client) }
        }
    }
}
