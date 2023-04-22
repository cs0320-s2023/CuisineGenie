import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

const NavigationWrapper = styled.nav`
  display: flex;
  width: 70%;
  margin: 24px auto 16px;
  justify-content: space-between;
`;

/**
 * React Component for Header
 * @returns JSX.Element to print header on screen
 */
function Header() {
  return (
    <div>
      <h1
        aria-label="REPL Header"
        aria-description="Welcome to REPL. Click tab to navigate to search bar, and enter commands."
      >
        REPL
      </h1>
      <NavigationWrapper>
        <Link to={"index.html"}>quiz</Link>
        <Link to={""}>results</Link>
        <Link to={""}>profile</Link>
      </NavigationWrapper>
    </div>
  );
}

export default Header;
