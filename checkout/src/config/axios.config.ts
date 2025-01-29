import { Logger } from '@nestjs/common';
import axios, { AxiosInstance } from 'axios';
import axiosRetry from 'axios-retry';

let attemptCount = 0;

export const createAxiosInstance = (): AxiosInstance => {
    const logger = new Logger('AxiosInstance');

    const instance = axios.create({
        timeout: 5000, // Timeout de 5 segundos
        headers: {
            'Content-Type': 'application/json',
        },
    });

    axiosRetry(instance, {
        retries: 3,
        retryDelay: (retryCount) => {
            return axiosRetry.exponentialDelay(retryCount) * 3;
        },
        retryCondition: (error) => {
            attemptCount++;
            logger.warn(`Something went wrong, trying again: Attempt number #${attemptCount}`);
            return error && error.response && error.response.status === 500 || false;
        },
        onMaxRetryTimesExceeded: () => {
            attemptCount = 0;
        }
    });

    instance.interceptors.response.use(
        (response) => response,
        (error) => {
            return Promise.reject(error);
        },
    );

    return instance;
};