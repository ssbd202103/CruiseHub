import {createSelector, createSlice, PayloadAction} from "@reduxjs/toolkit";

const tokenSlice = createSlice({
    name: 'token',
    initialState: '',
    reducers: {
        update: (state: string, {payload}: PayloadAction<string>) => payload
    }
})

export const {update} = tokenSlice.actions

const selectToken = createSelector(
    (state: {token: string}) => state,
    state => state.token
)

export default tokenSlice.reducer