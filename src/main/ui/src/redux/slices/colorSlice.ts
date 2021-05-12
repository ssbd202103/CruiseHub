import { createSlice, createSelector } from '@reduxjs/toolkit'

const colorSlice = createSlice({
    name: 'color',
    initialState: {
        color: true
    },
    reducers: {
        change: state => {
            state.color = !state.color
        }
    }
})

export const {change} = colorSlice.actions

export const selectColor= (state: any) => state.color

export default colorSlice.reducer