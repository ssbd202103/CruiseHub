import ArrowIcon from '@material-ui/icons/ExpandMoreRounded'

import CruiseCard from '../components/CruiseCard'

import HeaderFooterLayout from '../layouts/HeaderFooterLayout'

import styles from '../styles/Home.module.css'

import {useTranslation} from 'react-i18next'
import {useSelector} from "react-redux";
import {selectDarkMode} from "../redux/slices/userSlice";


export default function Home() {
    const {t} = useTranslation()

    const darkMode = useSelector(selectDarkMode)
    const headersArray = t("mainPage.headers", {returnObjects: true})

    return (
        <HeaderFooterLayout>
            <header className={styles.header}>
                <h1>{headersArray[Math.floor(Math.random() * headersArray.length)]}</h1>
                <ArrowIcon className={styles.arrow}/>
            </header>

            <section className={styles.ad}>
                <div>{t("ad")}</div>
            </section>

            <section className={styles['top-cruises']}>
                <h2 className={styles.h2}
                    style={{color: `var(--${darkMode ? 'dark' : 'white'}`}}>{t("the best cruises")}</h2>

                <div className={styles['cruises-grid']}>
                    {[0, 1, 2, 3, 4, 5, 6, 7, 8].map(item =>
                        <CruiseCard key={item}/>)}
                </div>
            </section>
        </HeaderFooterLayout>
    )
}
