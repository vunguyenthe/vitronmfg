import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/slide-my-suffix">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Slide My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/main-product-my-suffix">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Main Product My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/product-my-suffix">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Product My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/category-my-suffix">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Category My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/event-my-suffix">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Event My Suffix
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/news-my-suffix">
      <FontAwesomeIcon icon="asterisk" />&nbsp;News My Suffix
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
