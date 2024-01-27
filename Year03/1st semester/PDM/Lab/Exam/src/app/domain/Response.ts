import { Product } from "./Product";

export class ServerResponse {
  total: number | undefined;
  page: number | undefined;
  products: Product[] = [];
}
