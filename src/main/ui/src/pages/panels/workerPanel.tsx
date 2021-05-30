import {useTranslation} from 'react-i18next'
import SettingsIcon from '@material-ui/icons/SettingsRounded'
import CruiseIcon from '@material-ui/icons/CardTravelRounded'
import {useSelector} from "react-redux";
import {selectDarkMode} from "../../redux/slices/userSlice";
import ChangeBusinessWorkerData from "../../components/changeData/ChangeBusinessWorkerData";
import React, {useState} from "react";
import ChangeEmail from "../../components/changeData/ChangeEmail";
import ChangePassword from "../../components/changeData/ChangePassword";
import PanelLayout from "../../layouts/PanelLayout";

export default function WorkerPanel() {
    const {t} = useTranslation()

    const darkMode = useSelector(selectDarkMode)

    const [isEmailEdit, setIsEmailEdit] = useState(false)
    const [isDataEdit, setIsDataEdit] = useState(false)
    const [isPasswordEdit, setIsPasswordEdit] = useState(false)

    const handleIsEmailEdit = () => {
        setIsEmailEdit(true)
        setIsDataEdit(false)
        setIsPasswordEdit(false)
    }

    const handleIsDataEdit = () => {
        setIsDataEdit(true)
        setIsEmailEdit(false)
        setIsPasswordEdit(false)
    }

    const handleIsPasswordEdit = () => {
        setIsPasswordEdit(true)
        setIsDataEdit(false)
        setIsEmailEdit(false)
    }

    return (
        <PanelLayout
            color={{
                light: 'green-dark',
                dark: 'white'
            }}
            menu={[
                {
                    link: '/cruises',
                    text: t('cruises'),
                    Icon: CruiseIcon,
                    Component: () => <></>
                },
                {
                    link: '/settings',
                    text: t('settings'),
                    Icon: SettingsIcon,
                    Component: () => (
                        <>
                            <ChangeBusinessWorkerData
                                open={isDataEdit}
                                onOpen={handleIsDataEdit}
                                onConfirm={() => {setIsDataEdit(false)}}
                                onCancel={() => {setIsDataEdit(false)}}
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
