import ArrowIcon from '@material-ui/icons/ExpandMoreRounded'

import CruiseCard from '../components/CruiseCard'

import HeaderFooterLayout from '../layouts/HeaderFooterLayout'

import styles from '../styles/Home.module.css'

import {useTranslation} from 'react-i18next'
import {useSelector} from "react-redux";
import {selectDarkMode} from "../redux/slices/userSlice";
import {useEffect} from "react";
import {getPublishedCruises} from "../Services/cruisesService";


export default function Home() {
    const {t} = useTranslation()

    const darkMode = useSelector(selectDarkMode)
    const headersArray = t("mainPage.headers", {returnObjects: true})

    useEffect(() => {
        getPublishedCruises().then()
    }, []);

    return (
        <HeaderFooterLayout>
            <header className={styles.header}>
                <h1>{headersArray[Math.floor(Math.random() * headersArray.length)]}</h1>
                <ArrowIcon className={styles.arrow}/>
            </header>

            <section className={styles['top-cruises']}>
                <h2 className={styles.h2}
                    style={{color: `var(--${darkMode ? 'white' : 'dark'}`}}>{t("cruises")}</h2>

                <div className={styles['cruises-grid']}>
                </div>
            </section>
        </HeaderFooterLayout>
    )
}
