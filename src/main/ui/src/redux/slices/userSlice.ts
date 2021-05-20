import {createSelector, createSlice, PayloadAction} from '@reduxjs/toolkit'

export interface IUserSliceState {
    firstName: string,
    secondName: string,
    email: string,
    login: string,
    etag: string,
    darkMode: boolean,
    version: number,
    languageType: string,
    accessLevels: Array<IAccessLevel>
}

export interface IAccessLevel {
    '@type': "client" | "businessWorker" | "moderator" | "administrator",
    accessLevelType: "CLIENT" | "BUSINESS_WORKER" | "MODERATOR" | "ADMINISTRATOR",
    address?: {
        houseNumber: number,
        street: string,
        postalCode: string,
        city: string,
        country: string
    },
    phoneNumber?: string,
    companyName?: string
}

const userSlice = createSlice({
    name: 'user',
    initialState: {
        firstName: '',
        secondName: '',
        login: '',
        darkMode: false,
        email: '',
        languageType: 'PL',
        accessLevels: [] as Array<IAccessLevel>,
        etag: '',
        version: 0
    } as IUserSliceState,
    reducers: {
        setUser: (state: IUserSliceState, {payload}: PayloadAction<IUserSliceState>) => payload,
        changeEmail: (state: IUserSliceState, {payload}: PayloadAction<string>) => {
            state.email = payload
        },
        emptyUser: (state: IUserSliceState) => ({
            firstName: '',
            secondName: '',
            login: '',
            darkMode: false,
            email: '',
            languageType: 'PL',
            accessLevels: [] as Array<IAccessLevel>,
            etag: '',
            version: 0
        })
    }
})

export const {setUser, changeEmail, emptyUser} = userSlice.actions

const selectSelf = (state: { user: IUserSliceState }) => state

export const selectFirstName = createSelector(selectSelf, state => state.user.firstName)
export const selectSecondName = createSelector(selectSelf, state => state.user.secondName)
export const selectEmail = createSelector(selectSelf, state => state.user.email)
export const selectEtag = createSelector(selectSelf, state => state.user.etag)
export const selectLogin = createSelector(selectSelf, state => state.user.login)
export const selectVersion = createSelector(selectSelf, state => state.user.version)
export const selectDarkMode = createSelector(selectSelf, state => state.user.darkMode)
export const isUserEmpty = createSelector(selectSelf, state => {
    const {
        firstName,
        secondName,
        login,
        email,
        accessLevels,
        etag
    } = state.user

    return firstName && secondName && login && email && accessLevels.length && etag
})

export const selectPhoneNumber = (accessLevelLabel: "CLIENT" | "BUSINESS_WORKER") =>
    createSelector(selectSelf,
            state => state.user.accessLevels.find(accessLevel => accessLevel.accessLevelType === accessLevelLabel)?.phoneNumber || "-1")

export const selectAddress =
    createSelector(selectSelf,
        state => state.user.accessLevels.find(accessLevel => accessLevel.accessLevelType === "CLIENT")?.address ||
            {
                houseNumber: 0,
                street: "",
                postalCode: "",
                city: "",
                country: ""
            })

export default userSlice.reducer