package org.javacs.kt

import org.eclipse.lsp4j.CodeAction
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment
import java.util.concurrent.CompletableFuture

@JsonSegment("kotlin")
interface KotlinProtocolExtensions {
    @JsonRequest
    fun jarClassContents(textDocument: TextDocumentIdentifier): CompletableFuture<String?>

    @JsonRequest
    fun buildOutputLocation(): CompletableFuture<String?>

    @JsonRequest
    fun mainClass(textDocument: TextDocumentIdentifier): CompletableFuture<Map<String, Any?>>

    @JsonRequest
    fun overrideMember(position: TextDocumentPositionParams): CompletableFuture<List<CodeAction>>
}
