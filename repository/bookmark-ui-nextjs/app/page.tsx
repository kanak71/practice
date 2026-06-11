import Image from "next/image";
import styles from "./page.module.css";
import NavBar from "./component/NavBar";

export default function Home() {
  return (
    <div className="container">
      <NavBar/>
      <main>
        <h2>Welcome to Bookmark</h2>
      </main>
    </div>
  );
}
