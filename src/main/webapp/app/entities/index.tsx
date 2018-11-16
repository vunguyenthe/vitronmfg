import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SlideMySuffix from './slide-my-suffix';
import MainProductMySuffix from './main-product-my-suffix';
import ProductMySuffix from './product-my-suffix';
import CategoryMySuffix from './category-my-suffix';
import EventMySuffix from './event-my-suffix';
import NewsMySuffix from './news-my-suffix';
import LogoMySuffix from './logo-my-suffix';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/slide-my-suffix`} component={SlideMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/main-product-my-suffix`} component={MainProductMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/product-my-suffix`} component={ProductMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/category-my-suffix`} component={CategoryMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/event-my-suffix`} component={EventMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/news-my-suffix`} component={NewsMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/logo-my-suffix`} component={LogoMySuffix} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
