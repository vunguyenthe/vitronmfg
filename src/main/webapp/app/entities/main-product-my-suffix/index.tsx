import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MainProductMySuffix from './main-product-my-suffix';
import MainProductMySuffixDetail from './main-product-my-suffix-detail';
import MainProductMySuffixUpdate from './main-product-my-suffix-update';
import MainProductMySuffixDeleteDialog from './main-product-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MainProductMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MainProductMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MainProductMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={MainProductMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MainProductMySuffixDeleteDialog} />
  </>
);

export default Routes;
