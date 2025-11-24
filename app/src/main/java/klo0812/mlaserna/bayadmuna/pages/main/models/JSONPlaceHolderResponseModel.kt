package klo0812.mlaserna.bayadmuna.pages.main.models

class JSONPlaceHolderResponseModel(
    val id: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val body: String? = null,
    var code: Int = -1,
    var message: String? = null,
    val exception: Exception? = null
) {

    companion object {
        const val CODE_GENERIC_ERROR = 86236
    }

}