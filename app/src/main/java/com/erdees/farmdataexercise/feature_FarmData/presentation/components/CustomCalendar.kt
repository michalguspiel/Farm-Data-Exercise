package com.erdees.farmdataexercise.feature_FarmData.presentation.components

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionHandler
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.LocalDate
import java.time.YearMonth


class CustomSelectionState(
    private val onSelectionChanged: (List<LocalDate>) -> Unit,
    selection: List<LocalDate> = emptyList(),
    selectionMode: SelectionMode = SelectionMode.Period,
) : SelectionState {

    private var _selection by mutableStateOf(selection)
    private var _selectionMode by mutableStateOf(selectionMode)

    var selection: List<LocalDate>
        get() = _selection
        set(value) {
            if (value != selection) {
                _selection = value
                onSelectionChanged(value)
            }
        }

    var selectionMode: SelectionMode
        get() = _selectionMode
        set(value) {
            if (value != selectionMode) {
                _selection = emptyList()
                _selectionMode = value
            }
        }

    override fun isDateSelected(date: LocalDate): Boolean {
        return selection.contains(date)
    }

    override fun onDateSelected(date: LocalDate) {
        if (selection.isNotEmpty() && date == selection.last()) {
            selection = emptyList()
        }
        selection = DynamicSelectionHandler.calculateNewSelection(date, selection, selectionMode)
    }

    internal companion object {
        @Suppress("FunctionName") // Factory function
        fun Saver(onSelectionChanged: (List<LocalDate>) -> Unit): Saver<CustomSelectionState, Any> =
            listSaver(
                save = { saverScope ->
                    listOf(saverScope.selectionMode, saverScope.selection.map { it.toString() })
                },
                restore = { restored ->
                    CustomSelectionState(
                        onSelectionChanged = onSelectionChanged,
                        selectionMode = restored[0] as SelectionMode,
                        selection = (restored[1] as? List<String>)?.map { LocalDate.parse(it) }
                            .orEmpty(),
                    )
                }
            )
    }
}


@Composable
fun rememberSelectionState(
    initialMonth: YearMonth = YearMonth.now(),
    initialSelection: List<LocalDate> = emptyList(),
    initialSelectionMode: SelectionMode = SelectionMode.Period,
    onSelectionChanged: (List<LocalDate>) -> Unit = {},
    monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
        MonthState(initialMonth = initialMonth)
    },
    selectionState: CustomSelectionState = rememberSaveable(
        saver = CustomSelectionState.Saver(
            onSelectionChanged
        )
    ) {
        CustomSelectionState(onSelectionChanged, initialSelection, initialSelectionMode)
    },
): CalendarState<CustomSelectionState> = remember { CalendarState(monthState, selectionState) }
