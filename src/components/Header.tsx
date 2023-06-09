import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

const NavigationWrapper = styled.nav`
  display: flex;
  width: 70%;
  margin: 24px auto 16px;
  justify-content: space-between;
`;

export const Header_AriaLabel = "CuisineGenie";
export const Header_Role = "Application header"
export const Header_Describedby = "Welcome to Cuisine Genie. Cuisine Genie is an application where users are able to generate recipes they will like based on their selection of meals in the quiz page. They will be able to see their generated recipes in the results page"

/**
 * React Component for Header
 * @returns JSX.Element to print header on screen
 */
function Header() {
  return (
    <div>
      <h1
        aria-label={Header_AriaLabel}
        aria-roledescription = {Header_Role}
        aria-describedby= {Header_Describedby}
      >
        CuisineGenie
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
