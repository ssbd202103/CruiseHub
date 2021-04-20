import Link from 'next/link'

import ArrowIcon from '@material-ui/icons/ExpandMoreRounded'

import CruiseCard from '../components/CruiseCard'

import HeaderFooterLayout from '../layouts/HeaderFooterLayout'

import styles from '../styles/Home.module.css'


export default function Home() {
    return (
        <HeaderFooterLayout>
            <header className={styles.header}>
                <ArrowIcon className={styles.arrow} />
            </header>

            <section className={styles.ad}>
                <div>REKLAMA</div>
            </section>

            <section className={styles['top-cruises']}>
                <h2 className={styles.h2}>Najleprze wycieczki</h2>

                <div className={styles['cruises-grid']}>
                    {[0,1,2,3,4,5,6,7,8].map(item => 
                        <CruiseCard key={item} />)}
                </div>
            </section>
        </HeaderFooterLayout>
    )
}
