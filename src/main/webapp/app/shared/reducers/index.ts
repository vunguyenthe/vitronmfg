import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import slide, {
  SlideMySuffixState
} from 'app/entities/slide-my-suffix/slide-my-suffix.reducer';
// prettier-ignore
import mainProduct, {
  MainProductMySuffixState
} from 'app/entities/main-product-my-suffix/main-product-my-suffix.reducer';
// prettier-ignore
import product, {
  ProductMySuffixState
} from 'app/entities/product-my-suffix/product-my-suffix.reducer';
// prettier-ignore
import category, {
  CategoryMySuffixState
} from 'app/entities/category-my-suffix/category-my-suffix.reducer';
// prettier-ignore
import event, {
  EventMySuffixState
} from 'app/entities/event-my-suffix/event-my-suffix.reducer';
// prettier-ignore
import news, {
  NewsMySuffixState
} from 'app/entities/news-my-suffix/news-my-suffix.reducer';
// prettier-ignore
import logo, {
  LogoMySuffixState
} from 'app/entities/logo-my-suffix/logo-my-suffix.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly slide: SlideMySuffixState;
  readonly mainProduct: MainProductMySuffixState;
  readonly product: ProductMySuffixState;
  readonly category: CategoryMySuffixState;
  readonly event: EventMySuffixState;
  readonly news: NewsMySuffixState;
  readonly logo: LogoMySuffixState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  slide,
  mainProduct,
  product,
  category,
  event,
  news,
  logo,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
