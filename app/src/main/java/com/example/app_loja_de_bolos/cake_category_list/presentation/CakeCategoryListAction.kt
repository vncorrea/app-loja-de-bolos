sealed class CakeCategoryAction {
    data class UpdateCategoryList(val categories: List<String>) : CakeCategoryAction()
    object ShowErrorMsg : CakeCategoryAction()
}