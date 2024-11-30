import com.example.app_loja_de_bolos.cake_category_list.model.CakeCategory

sealed class CakeCategoryAction {
    data class UpdateCategoryList(val categories: List<CakeCategory>) : CakeCategoryAction()
    object ShowErrorMsg : CakeCategoryAction()
    data class NavigateToCakeList(val category: String) : CakeCategoryAction()
}