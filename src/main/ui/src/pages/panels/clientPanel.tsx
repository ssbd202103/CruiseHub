import SettingsIcon from '@material-ui/icons/SettingsRounded'
import CruiseIcon from '@material-ui/icons/CardTravelRounded'

import {useTranslation} from 'react-i18next'

import {useSelector} from "react-redux";
import {selectDarkMode} from "../../redux/slices/userSlice";
import ChangeEmail from "../../components/changeData/ChangeEmail";
import React, {useState} from "react";
import ChangeClientData from "../../components/changeData/ChangeClientData";
import ChangePassword from "../../components/changeData/ChangePassword";
import ChangeAddress from "../../components/changeData/ChangeAddress";
import PanelLayout from "../../layouts/PanelLayout";

export default function ClientPanel() {
    const { t } = useTranslation()

    const [isEmailEdit, setIsEmailEdit] = useState(false)
    const [isDataEdit, setIsDataEdit] = useState(false)
    const [isAddressEdit, setIsAddressEdit] = useState(false)
    const [isPasswordEdit, setIsPasswordEdit] = useState(false)

    const handleIsEmailEdit = () => {
        setIsEmailEdit(true)
        setIsDataEdit(false)
        setIsAddressEdit(false)
        setIsPasswordEdit(false)
    }

    const handleIsDataEdit = () => {
        setIsDataEdit(true)
        setIsEmailEdit(false)
        setIsAddressEdit(false)
        setIsPasswordEdit(false)
    }

    const handleIsAddressEdit = () => {
        setIsAddressEdit(true)
        setIsDataEdit(false)
        setIsEmailEdit(false)
        setIsPasswordEdit(false)
    }

    const handleIsPasswordEdit = () => {
        setIsPasswordEdit(true)
        setIsDataEdit(false)
        setIsEmailEdit(false)
        setIsAddressEdit(false)
    }

    return (
        <PanelLayout
            color={{
                light: 'blue',
                dark: 'white-dark'
            }}
            menu={[
                {
                    link: '/profile/cruises',
                    text: t('cruises'),
                    Icon: CruiseIcon,
                    Component: () => <></>
                },
                {
                    link: '/profile/settings',
                    text: t('settings'),
                    Icon: SettingsIcon,
                    Component: () => (
                        <>
                            <ChangeClientData
                                open={isDataEdit}
                                onOpen={handleIsDataEdit}
                                onConfirm={() => {setIsDataEdit(false)}}
                                onCancel={() => {setIsDataEdit(false)}}
                            />
                            <ChangeAddress
                                open={isAddressEdit}
                                onOpen={handleIsAddressEdit}
                                onConfirm={() => {setIsAddressEdit(false)}}
                                onCancel={() => {setIsAddressEdit(false)}}
                            />
                            <ChangeEmail
                                open={isEmailEdit}
                                onOpen={handleIsEmailEdit}
                                onConfirm={() => {setIsEmailEdit(false)}}
                                onCancel={() => {setIsEmailEdit(false)}}
                            />
                            <ChangePassword
                                open={isPasswordEdit}
                                onOpen={handleIsPasswordEdit}
                                onConfirm={() => {setIsPasswordEdit(false)}}
                                onCancel={() => {setIsPasswordEdit(false)}}
                            />
                        </>
                    )
                }
            ]}
        />
    )
}
