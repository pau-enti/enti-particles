package com.example.particles.ui.quotes.todo

// JSON to koltin class
// Proprety: Auto determine Nullable
// Other: Enable inner class model
// Extensions: Let propreties cammel names

data class Photos(
    val collection: Collection
) {
    data class Collection(
        val href: String,
        val items: List<Item>,
        val links: List<Link>,
        val metadata: Metadata,
        val version: String
    ) {
        data class Item(
            val `data`: List<Data>,
            val href: String,
            val links: List<Link>
        ) {
            data class Data(
                val album: List<String>?,
                val center: String,
                val dateCreated: String,
                val description: String,
                val description508: String?,
                val keywords: List<String>?,
                val location: String?,
                val mediaType: String,
                val nasaId: String,
                val photographer: String?,
                val secondaryCreator: String?,
                val title: String
            )

            data class Link(
                val href: String,
                val rel: String,
                val render: String?
            )
        }

        data class Link(
            val href: String,
            val prompt: String,
            val rel: String
        )

        data class Metadata(
            val totalHits: Int
        )
    }
}