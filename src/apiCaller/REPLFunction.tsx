/**
 * Interface that shows what a REPLFunction must take in and return
 */
export interface REPLFunction {
  (args: string[]): Promise<string>;
}
