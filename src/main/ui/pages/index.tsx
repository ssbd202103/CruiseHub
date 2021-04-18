import Link from 'next/link'


export default function Home() {
    return (
        <div>
            <h1>Hello, Next.js with Redux!</h1>
            <Link href="/example">Go to example page</Link> <br/>
            <Link href="/sign-up">Don't have an account? Sign up now!</Link> 
        </div>
    )
}
