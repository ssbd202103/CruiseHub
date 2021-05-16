import {createSlice, PayloadAction} from "@reduxjs/toolkit";

const tokenSlice = createSlice({
    name: 'token',
    initialState: '',
    reducers: {
        update: (state: string, {payload}: PayloadAction<string>) => payload
    }
})

export const {update} = tokenSlice.actions

export default tokenSlice.reducer