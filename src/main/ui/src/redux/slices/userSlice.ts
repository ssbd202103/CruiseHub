import {createSelector, createSlice, PayloadAction} from '@reduxjs/toolkit'
import i18n from "i18next";
import {saveUser} from "../../Services/userService";

export interface ILoginUserSliceState {
    firstName: string,
    secondName: string,
    email: string,
    login: string,
    etag: string,
    darkMode: boolean,
    version: number,
    languageType: string,
    accessLevels: Array<IAccessLevel>,
}

export interface IUserSliceState extends ILoginUserSliceState {
    activeAccessLevel: AccessLevelType,
}

export type AccessLevelType = "CLIENT" | "BUSINESS_WORKER" | "MODERATOR" | "ADMINISTRATOR" | "";

export interface IAccessLevel {
    '@type': "client" | "businessWorker" | "moderator" | "administrator",
    accessLevelType: AccessLevelType,
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
        languageType: window.navigator.languages[1].toUpperCase() === "EN" ? "EN" : "PL",
        accessLevels: [] as Array<IAccessLevel>,
        activeAccessLevel: '',
        etag: '',
        version: 0
    } as IUserSliceState,
    reducers: {
        setUser: (state: IUserSliceState, {payload}: PayloadAction<ILoginUserSliceState>) => ({
            ...payload,
            activeAccessLevel:
                payload.accessLevels
                    .find(accessLevel => accessLevel.accessLevelType === state.activeAccessLevel)?.accessLevelType || payload.accessLevels[0].accessLevelType //state.activeAccessLevel || payload.accessLevels[0].accessLevelType
        }),
        changeEmail: (state: IUserSliceState, {payload}: PayloadAction<string>) => {
            state.email = payload
        },
        emptyUser: (state: IUserSliceState) => {
            const languageType = window.navigator.languages[1]
                .toUpperCase() === "PL" ? "PL" : "EN"
            i18n.changeLanguage(languageType)
            return {
                firstName: '',
                secondName: '',
                login: '',
                darkMode: false,
                email: '',
                languageType,
                accessLevels: [] as Array<IAccessLevel>,
                activeAccessLevel: '',
                etag: '',
                version: 0
            } as IUserSliceState
        },
        setActiveAccessLevel: (state: IUserSliceState, {payload}: PayloadAction<AccessLevelType>) => {
            state.activeAccessLevel = payload
            sessionStorage.setItem('cruisehub_user', JSON.stringify({...state, activeAccessLevel: payload}))
        }
    }
})

export const {setUser, changeEmail, emptyUser, setActiveAccessLevel} = userSlice.actions

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
        etag
    } = state.user

    return !firstName && !secondName && !login && !email && !etag
})
export const getAccessLevelLabels = createSelector(selectSelf, state =>
    state.user.accessLevels.map((accessLevel: IAccessLevel) => accessLevel.accessLevelType)
)

export const selectOtherAccessLevel = createSelector(selectSelf, state =>
    state.user.accessLevels
        .map((accessLevel: IAccessLevel) => accessLevel.accessLevelType)
        .filter(label => label !== state.user.activeAccessLevel))

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

export const selectActiveAccessLevel = createSelector(selectSelf, state => state.user.activeAccessLevel)

export default userSlice.reducer