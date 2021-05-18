import {createSelector, createSlice, PayloadAction} from "@reduxjs/toolkit";

const tokenSlice = createSlice({
    name: 'token',
    initialState: '',
    reducers: {
        update: (state: string, {payload}: PayloadAction<string>) => payload
    }
})

export const {update} = tokenSlice.actions

const selectSelf = (state: {token: string}) => state

export const selectToken = createSelector(selectSelf, state => state.token)

export default tokenSlice.reducer