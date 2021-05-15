import {createSlice, createDraftSafeSelector, createSelector} from '@reduxjs/toolkit'

const colorSlice = createSlice({
    name: 'color',
    initialState: true,
    reducers: {
        change: state => !state
    }
})

export interface ColorSliceState {
    color: boolean
}

export const {change} = colorSlice.actions

const selectSelf = (state: ColorSliceState) => state

export const selectColor = createSelector(selectSelf, state => state.color)

export default colorSlice.reducer